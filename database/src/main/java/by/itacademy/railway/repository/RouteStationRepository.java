package by.itacademy.railway.repository;

import by.itacademy.railway.entity.RouteStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteStationRepository extends JpaRepository<RouteStation, Long> {

    Optional<RouteStation> findByTrainIdAndStationName(Long trainId, String stationName);

}
