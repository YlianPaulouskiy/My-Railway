package by.itacademy.railway.repository;

import by.itacademy.railway.entity.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @Query("SELECT up.passenger FROM UserPassenger up WHERE up.user.id = :userId")
    List<Passenger> findAllByUserId(@Param("userId") Long id);

    @Query("SELECT up.passenger FROM UserPassenger up WHERE up.user.id = :userId")
    Page<Passenger> findAllByUserId(@Param("userId") Long id, Pageable pageable);

}
