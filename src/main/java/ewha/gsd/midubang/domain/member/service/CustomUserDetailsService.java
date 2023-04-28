package ewha.gsd.midubang.domain.member.service;

import ewha.gsd.midubang.domain.member.entity.Member;
import ewha.gsd.midubang.domain.member.repository.MemberRepository;
import ewha.gsd.midubang.domain.member.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if(member==null){
            new UsernameNotFoundException("다음 이메일로 가입된 기록 없음("+email+")");
        }
        return UserPrincipal.create(member);
    }
}
