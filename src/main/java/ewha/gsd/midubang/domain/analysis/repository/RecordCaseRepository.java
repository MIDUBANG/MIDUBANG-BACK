package ewha.gsd.midubang.domain.analysis.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ewha.gsd.midubang.domain.analysis.entity.RecordCase;
import ewha.gsd.midubang.global.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.*;


import static ewha.gsd.midubang.domain.analysis.entity.QRecordCase.*;
import static ewha.gsd.midubang.domain.analysis.entity.QRecord.*;

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
    public List<Tuple> findAllRecordCasesById(Long recordId){
        queryFactory = new JPAQueryFactory(em);
        List<Tuple> caseList = queryFactory
                .select(
                        recordCase.aCase.id,
                        recordCase.aCase.case_detail,
                        recordCase.aCase.description,
                        recordCase.aCase.article_url,
                        recordCase.aCase.type,
                        recordCase.case_exists,
                        recordCase.raw_case,
                        recordCase.aCase.word_ref
                )
                .from(recordCase)
                .leftJoin(recordCase.record, record)
                .where(recordCase.record.recordId.eq(recordId))
                .fetch();

        return caseList;

    }

    @Transactional
    public void deleteRecordCase(Long recordId){
        queryFactory = new JPAQueryFactory(em);
        long count = queryFactory.delete(recordCase)
                .where(recordCase.record.recordId.eq(recordId))
                .execute();
        if(count==0){
            throw new ApiRequestException("recordId not exist");
        }
    }


}
