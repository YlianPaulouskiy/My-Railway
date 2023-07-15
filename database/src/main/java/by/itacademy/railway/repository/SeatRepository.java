package by.itacademy.railway.repository;

import by.itacademy.railway.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByWagonId(Long wagonId);

    @Query("select s From Seat s where s.wagon.train.id = :trainId and s.ticket = null")
    List<Seat> findAllByTrainId(Long trainId);
}
