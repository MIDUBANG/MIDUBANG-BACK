package ewha.gsd.midubang.domain.community.service;

import ewha.gsd.midubang.domain.community.entity.Answer;
import ewha.gsd.midubang.domain.community.entity.Comment;
import ewha.gsd.midubang.domain.community.entity.Post;
import ewha.gsd.midubang.domain.community.entity.Question;
import ewha.gsd.midubang.domain.community.repository.*;
import ewha.gsd.midubang.domain.community.dto.*;
import ewha.gsd.midubang.domain.member.entity.Member;
import ewha.gsd.midubang.domain.member.repository.MemberRepository;
import ewha.gsd.midubang.domain.community.dto.PostDetailDto;
import ewha.gsd.midubang.domain.community.dto.PostListDto;
import ewha.gsd.midubang.domain.community.dto.QuestionDetailDto;
import ewha.gsd.midubang.domain.community.dto.QuestionListDto;
import ewha.gsd.midubang.domain.community.dto.TodayResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostQuerydslRepository postQuerydslRepository;
    private final CommentRepository commentRepository;
    private final QuestionQuerydslRepository questionQuerydslRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

//    @Qualifier("openaiRestTemplate")
//    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String openaiApiKey;


    /* 금쪽이 글 작성 */
    public Long createPost(Long memberId, PostRequestDto request) {
        Post post = new Post();
        Member member = memberRepository.findByMemberId(memberId);
        // member null 예외처리

        post.setWriter(member);
        post.setQuestion(request.getQuestion());
        post.setDetail(request.getDetail());
        post.setCreatedDate(getCurrentDateTime());
        post.setComments(null);
        Post savedPost = postRepository.save(post);
        return savedPost.getPostId();
    }


    /* 금쪽이 글 삭제 */
    public DeleteResponseDto deletePost(Long memberId, Long postId) {
        // 작성자 확인
        Post post = postRepository.findById(postId).orElse(null);

        DeleteResponseDto responseDto = new DeleteResponseDto();
        if (post == null) {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setMessage("The post does not exist.");
            return responseDto;
        }

        if (post.getWriter().getMemberId() != memberId) {
            responseDto.setStatus(HttpStatus.FORBIDDEN);
            responseDto.setMessage("Only writer can delete.");
            return responseDto;
        }

        // 글 삭제
        postRepository.delete(post);
        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMessage("delete success.");
        return responseDto;
    }


    /* 금쪽이 글 상세 조회 */
    public PostDetailDto getPostDetail(Long postId) {

        if (postRepository.findById(postId).orElse(null) == null) {
            return null;
        }

        return postQuerydslRepository.findPostDetailByPostId(postId);
    }


    /* 금쪽이 글 목록 조회 */
    public List<PostListDto> getAllPosts() {
        return postQuerydslRepository.findAllPosts();
    }


    /* 금쪽이 댓글 작성 */
    public Long createComment(Long postId, Long memberId, CommentRequestDto request) {
        Comment comment = new Comment();
        Member member = memberRepository.findByMemberId(memberId);
        Post post = postQuerydslRepository.findByPostId(postId);
        // member, post null 예외처리

        comment.setMember(member);
        comment.setPost(post);
        comment.setContent(request.getComment());
        comment.setCreatedDate(getCurrentDateTime());

        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }


    /* 금쪽이 댓글 삭제 */
    public DeleteResponseDto deleteComment(Long memberId, Long commentId) {
        // 작성자 확인
        Comment comment = commentRepository.findById(commentId).orElse(null);

        DeleteResponseDto responseDto = new DeleteResponseDto();
        if (comment == null) {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setMessage("The comment does not exist.");
            return responseDto;
        }

        if (comment.getMember().getMemberId() != memberId) {
            responseDto.setStatus(HttpStatus.FORBIDDEN);
            responseDto.setMessage("Only writer can delete.");
        }

        // 댓글 삭제
        commentRepository.delete(comment);
        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMessage("delete success.");
        return responseDto;
    }


    /* 챗쪽이 글 업로드 */
    /* 매일 오전 6시에 실행 */
    private HttpEntity<ChatRequest> getHttpEntity(ChatRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);

        HttpEntity<ChatRequest> httpRequest = new HttpEntity<>(chatRequest, headers);
        return httpRequest;
    }

    @Scheduled(cron = "0 0 6 * * *", zone = "Asia/Seoul")
    public void getDailyQuestions() {

        List<Question> newQuestionList = new ArrayList<>();

        List<String> trueQuestions = questionQuerydslRepository.findAllTrueQuestions();

        String query1 = "임대차 계약에 대해 잘 모르는 사회초년생이 할 법한 질문 1개를 알려줘.";
        query1 += "\n조건은 다음과 같아.";
        query1 += "\n1.사회초년생에게 도움이 될 만한 진지한 질문이어야 해.";
        query1 += "\n2.다음 리스트에 있는 질문과 비슷한 질문이면 안돼:\n";
        query1 += trueQuestions;
        query1 += "\n3.답변 형식은 '진지한 질문: {진지한 질문}' 이 형식으로 적어줘";

        //log.info(query1);

        // Create a request
        ChatRequest request1 = new ChatRequest(model, query1);

        //log.info(request.toString());

        // Call the API
        RestTemplate restTemplate1 = new RestTemplate();
        ChatResponse response1 = restTemplate1.postForObject(apiUrl, getHttpEntity(request1), ChatResponse.class);

        if (response1 == null || response1.getChoices() == null || response1.getChoices().isEmpty()) {
            throw new RuntimeException();
        }

        String trueQuestion = response1.getChoices().get(0).getMessage().getContent().substring(8);
        Question newQuestion1 = new Question(trueQuestion, getCurrentDate(), Boolean.TRUE, null);
        newQuestionList.add(newQuestion1);


        //

        List<String> falseQuestions = questionQuerydslRepository.findAllFalseQuestions();

        String query2 = "임대차 계약에 대해 잘 모르는 사회초년생이 할 법한 질문 1개를 알려줘.";
        query2 += "\n조건은 다음과 같아.";
        query2 += "\n1.황당하고 엉뚱한 질문이어야 해.";
        query2 += "\n2.다음 리스트에 있는 질문과 비슷한 내용이면 안돼:\n";
        query2 += falseQuestions;
        query2 += "\n3.답변 형식은 '황당하고 엉뚱한 질문: {황당하고 엉뚱한 질문}' 이 형식으로 적어줘";

        //log.info(query2);

        ChatRequest request2 = new ChatRequest(model, query2);

        RestTemplate restTemplate2 = new RestTemplate();
        ChatResponse response2 = restTemplate2.postForObject(apiUrl, getHttpEntity(request2), ChatResponse.class);

        if (response2 == null || response2.getChoices() == null || response2.getChoices().isEmpty()) {
            throw new RuntimeException();
        }

        String falseQuestion = response2.getChoices().get(0).getMessage().getContent().substring(13);
        Question newQuestion2 = new Question(falseQuestion, getCurrentDate(), Boolean.FALSE, null);
        newQuestionList.add(newQuestion2);

        questionRepository.saveAll(newQuestionList);
    }


    /* 챗쪽이 글 상세 조회 */
    public QuestionDetailDto getQuestionDetail(Long questionId) {
        if (questionRepository.findById(questionId).orElse(null) == null)
            return null;

        return questionQuerydslRepository.findQuestionDetailById(questionId);
    }


    /* 챗쪽이 글 목록 조회 */
    public List<QuestionListDto> getAllQuestions() {
        return questionQuerydslRepository.findAllQuestions();
    }


    /* 챗쪽이 댓글 작성 */
    public Long createAnswer(Long questionId, Long memberId, CommentRequestDto requestDto) {
        Answer answer = new Answer();
        Member member = memberRepository.findByMemberId(memberId);
        Question question = questionRepository.findById(questionId).orElse(null);

        answer.setMember(member);
        answer.setQuestion(question);
        answer.setContent(requestDto.getComment());
        answer.setCreatedDate(getCurrentDateTime());

        Answer savedAnswer = answerRepository.save(answer);
        return savedAnswer.getId();
    }


    /* 챗쪽이 댓글 삭제 */
    public DeleteResponseDto deleteAnswer(Long memberId, Long answerId) {
        // 작성자 확인
        Answer answer = answerRepository.findById(answerId).orElse(null);

        DeleteResponseDto responseDto = new DeleteResponseDto();
        if (answer == null) {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setMessage("The answer does not exist.");
            return responseDto;
        }
        if (answer.getMember().getMemberId() != memberId) {
            responseDto.setStatus(HttpStatus.FORBIDDEN);
            responseDto.setMessage("only writer can delete.");
            return responseDto;
        }

        // 댓글 삭제
        answerRepository.delete(answer);
        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMessage("delete success.");
        return responseDto;
    }


    /* 오늘의 질문 */
    public TodayResponseDto getTodayQuestions() {
        String today = getCurrentDate();
        List<QuestionListDto> questions = questionQuerydslRepository.findTodayQuestions(today);
        List<PostListDto> posts = postQuerydslRepository.findTodayPosts(today);

        TodayResponseDto todayResponseDto = new TodayResponseDto(
                questions,
                posts
        );

        return todayResponseDto;
    }

    private String getCurrentDateTime() {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime zdtSeoul = instant.atZone(zoneId);
        String time = zdtSeoul.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return time;
    }

    private String getCurrentDate() {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime zdtSeoul = instant.atZone(zoneId);
        String date = zdtSeoul.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return date;
    }
}
