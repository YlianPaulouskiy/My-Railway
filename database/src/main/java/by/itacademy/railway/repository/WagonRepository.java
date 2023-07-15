package by.itacademy.railway.repository;

import by.itacademy.railway.entity.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WagonRepository extends JpaRepository<Wagon, Long> {

    List<Wagon> findAllByTrainId(Long trainId);

}
