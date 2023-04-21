package ewha.gsd.midubang.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewha.gsd.midubang.dto.response.MyCaseDto;

import ewha.gsd.midubang.dto.response.SimpleWordDto;
import ewha.gsd.midubang.entity.RecordCase;
import ewha.gsd.midubang.entity.Word;
import ewha.gsd.midubang.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;


import static ewha.gsd.midubang.entity.QRecordCase.*;
import static ewha.gsd.midubang.entity.QRecord.*;

@Repository
@RequiredArgsConstructor
public class RecordCaseRepository {
    private final EntityManager em;

    JPAQueryFactory queryFactory;

    @Transactional
    public void save(RecordCase recordCase){
        em.persist(recordCase);
    }



    @Transactional
    public List<Tuple> findAllRecordCasesById(Long record_id){
        queryFactory = new JPAQueryFactory(em);
        List<Tuple> caseList = queryFactory
                .select(
                        recordCase.aCase.id,
                        recordCase.aCase.case_detail,
                        recordCase.aCase.desc,
                        recordCase.aCase.article_url,
                        recordCase.aCase.type,
                        recordCase.case_exists,
                        recordCase.raw_case,
                        recordCase.aCase.word_ref
                )
                .from(recordCase)
                .leftJoin(recordCase.record, record)
                .where(recordCase.record.record_id.eq(record_id))
                .fetch();

        return caseList;

    }

    @Transactional
    public void deleteRecordCase(Long record_id){
        queryFactory = new JPAQueryFactory(em);
        long count = queryFactory.delete(recordCase)
                .where(recordCase.record.record_id.eq(record_id))
                .execute();
        if(count==0){
            throw new ApiRequestException("record_id not exist");
        }
    }


}
