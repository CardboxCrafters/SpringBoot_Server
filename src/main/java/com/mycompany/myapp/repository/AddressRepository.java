package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.NameCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT ad FROM Address ad " +
            "INNER JOIN ad.nameCard nc " +
            "INNER JOIN nc.category cat " +
            "WHERE FUNCTION('ST_Distance_Sphere', POINT(:longitude, :latitude), POINT(ad.longitude, ad.latitude)) < :distanceThreshold " +
            "AND nc.isUser = false " +
            "AND cat.id = :categoryId")
    List<Address> findNearbyAddressByCategoryAndIsUserFalse(@Param("latitude") Double latitude,
                                                            @Param("longitude") Double longitude,
                                                            @Param("distanceThreshold") Double distanceThreshold,
                                                            @Param("categoryId") Optional<Long> categoryId);
    @Query("SELECT ad FROM Address ad " +
            "JOIN ad.nameCard nc " +
            "WHERE FUNCTION('ST_Distance_Sphere', POINT(:longitude, :latitude), POINT(ad.longitude, ad.latitude)) < :distanceThreshold " +
            "AND nc.isUser = false")
    List<Address> findNearbyAddressAndIsUserFalse(@Param("latitude") Double latitude,
                                                  @Param("longitude") Double longitude,
                                                  @Param("distanceThreshold") Double distanceThreshold);

    List<Address> findByNameCard(NameCard nameCard);
    Address findOneByNameCard(NameCard nameCard);
}
