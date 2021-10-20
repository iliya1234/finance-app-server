package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.dto.ObjectToTotalOperations;
import net.proselyte.jwtappdemo.model.TotalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TotalRepository extends JpaRepository<TotalEntity, Long> {
    @Query(
            value = "Select categories.name, sum(total) as \"total\"\n" +
                    "from operations\n" +
                    "inner join subcategories on operations.subcategory_id = subcategories.id\n" +
                    "inner join categories on subcategories.category_id = categories.id\n" +
                    "where DATE(created) = DATE(NOW()) and operations.type = \"Purchases\"\n" +
                    "and operations.user_id = :id_user\n" +
                    "group by categories.name\n" +
                    "order by total desc;",
            nativeQuery = true)
    List<ObjectToTotalOperations> todayTotalPurchases(@Param("id_user") Long id);

    @Query(
            value = "Select categories.name, sum(total) as \"total\"\n" +
                    "from operations\n" +
                    "inner join subcategories on operations.subcategory_id = subcategories.id\n" +
                    "inner join categories on subcategories.category_id = categories.id\n" +
                    "where DATE(created) = DATE(NOW()) and operations.type = \"Incomes\"\n" +
                    "and operations.user_id = :id_user\n" +
                    "group by categories.name\n" +
                    "order by total desc;",
            nativeQuery = true)
    List<ObjectToTotalOperations> todayTotalIncomes(@Param("id_user") Long id);

    @Query(
            value = "Select categories.name, sum(total) as \"total\"\n" +
                    "from operations\n" +
                    "inner join subcategories on operations.subcategory_id = subcategories.id\n" +
                    "inner join categories on subcategories.category_id = categories.id\n" +
                    "WHERE EXTRACT(Week FROM created) = EXTRACT(Week FROM current_timestamp)\n" +
                    "and EXTRACT(Year FROM created) = EXTRACT(Year FROM current_timestamp)\n" +
                    "and operations.type = \"Purchases\"\n" +
                    "and operations.user_id = :id_user\n" +
                    "group by categories.name\n" +
                    "order by total desc;",
            nativeQuery = true)
    List<ObjectToTotalOperations> weekTotalPurchases(@Param("id_user") Long id);

    @Query(
            value = "Select categories.name, sum(total) as \"total\"\n" +
                    "from operations\n" +
                    "inner join subcategories on operations.subcategory_id = subcategories.id\n" +
                    "inner join categories on subcategories.category_id = categories.id\n" +
                    "WHERE EXTRACT(Week FROM created) = EXTRACT(Week FROM current_timestamp)\n" +
                    "and EXTRACT(Year FROM created) = EXTRACT(Year FROM current_timestamp)\n" +
                    "and operations.type = \"Incomes\"\n" +
                    "and operations.user_id = :id_user\n" +
                    "group by categories.name\n" +
                    "order by total desc;",
            nativeQuery = true)
    List<ObjectToTotalOperations> weekTotalIncomes(@Param("id_user") Long id);


    @Query(
            value = "Select categories.name, sum(total) as \"total\"\n" +
                    "from operations\n" +
                    "inner join subcategories on operations.subcategory_id = subcategories.id\n" +
                    "inner join categories on subcategories.category_id = categories.id\n" +
                    "WHERE EXTRACT(Month FROM created) = EXTRACT(Month FROM current_timestamp)\n" +
                    "and EXTRACT(Year FROM created) = EXTRACT(Year FROM current_timestamp)\n" +
                    "and operations.type = \"Purchases\"\n" +
                    "and operations.user_id = :id_user\n" +
                    "group by categories.name\n" +
                    "order by total desc;",
            nativeQuery = true)
    List<ObjectToTotalOperations> monthTotalPurchases(@Param("id_user") Long id);

    @Query(
            value = "Select categories.name, sum(total) as \"total\"\n" +
                    "from operations\n" +
                    "inner join subcategories on operations.subcategory_id = subcategories.id\n" +
                    "inner join categories on subcategories.category_id = categories.id\n" +
                    "WHERE EXTRACT(Month FROM created) = EXTRACT(Month FROM current_timestamp)\n" +
                    "and EXTRACT(Year FROM created) = EXTRACT(Year FROM current_timestamp)\n" +
                    "and operations.type = \"Incomes\"\n" +
                    "and operations.user_id = :id_user\n" +
                    "group by categories.name\n" +
                    "order by total desc;",
            nativeQuery = true)
    List<ObjectToTotalOperations> monthTotalIncomes(@Param("id_user") Long id);

    @Query(
            value = "Select categories.name, sum(total) as \"total\"\n" +
                    "from operations\n" +
                    "inner join subcategories on operations.subcategory_id = subcategories.id\n" +
                    "inner join categories on subcategories.category_id = categories.id\n" +
                    "and EXTRACT(Year FROM created) = EXTRACT(Year FROM current_timestamp)\n" +
                    "and operations.type = \"Purchases\"\n" +
                    "and operations.user_id = :id_user\n" +
                    "group by categories.name\n" +
                    "order by total desc;",
            nativeQuery = true)
    List<ObjectToTotalOperations> yearTotalPurchases(@Param("id_user") Long id);

    @Query(
            value = "Select categories.name, sum(total) as \"total\"\n" +
                    "from operations\n" +
                    "inner join subcategories on operations.subcategory_id = subcategories.id\n" +
                    "inner join categories on subcategories.category_id = categories.id\n" +
                    "and EXTRACT(Year FROM created) = EXTRACT(Year FROM current_timestamp)\n" +
                    "and operations.type = \"Incomes\"\n" +
                    "and operations.user_id = :id_user\n" +
                    "group by categories.name\n" +
                    "order by total desc;",
            nativeQuery = true)
    List<ObjectToTotalOperations> yearTotalIncomes(@Param("id_user") Long id);
}
