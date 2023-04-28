package ewha.gsd.midubang.domain.analysis.repository;

import ewha.gsd.midubang.domain.analysis.entity.Case;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CaseRepository {

    private final EntityManager em;

    public Case findCaseById(Long caseId){
        return em.find(Case.class, caseId);

    }

}
