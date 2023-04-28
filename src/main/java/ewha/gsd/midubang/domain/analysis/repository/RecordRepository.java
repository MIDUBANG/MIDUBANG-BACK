package ewha.gsd.midubang.domain.analysis.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ewha.gsd.midubang.domain.analysis.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static ewha.gsd.midubang.domain.analysis.entity.QRecord.record;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    private final EntityManager em;
    JPAQueryFactory queryFactory;

    @Transactional
    public void save(Record record){
        em.persist(record);
    }

    public Record findRecordById(Long recordId){
        return em.find(Record.class, recordId);
    }

    public List<Record> findRecordListByMemberId(Long memberId){
        queryFactory = new JPAQueryFactory(em);
        List<Record> recordList = queryFactory
                .selectFrom(record)
                .where(record.member.memberId.eq(memberId))
                .fetch();


        return  recordList;
    }

    public void deleteRecord(Long recordId){
        queryFactory = new JPAQueryFactory(em);
        queryFactory.delete(record)
                .where(record.recordId.eq(recordId))
                .execute();
    }

}
