package kz.izzi.bus.repository;

import kz.izzi.bus.domain.LotteryRound;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LotteryRound entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LotteryRoundRepository extends JpaRepository<LotteryRound,Long> {
    
}
