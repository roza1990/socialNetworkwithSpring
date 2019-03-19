package com.example.demo.repository;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user  WHERE id !=:id",
            nativeQuery = true)
    List<User> findAllUsers(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value ="INSERT INTO user_request(user_id, friend_id,request) VALUES(:userId,:friendId,'SEND')",
            nativeQuery = true)
     void addFriendRequest(@Param("userId") int userId,@Param("friendId") int friendId);

    @Transactional
    @Modifying
    @Query(value ="SELECT * FROM `user`,`user_request` WHERE user.`id`=`user_request`.`user_id` AND `user_request`.`request`='SEND' AND `user_request`.`friend_id`=:reqId",
            nativeQuery = true)

    List<User> findAllRequestedUsers(@Param("reqId") Integer reqId);

    @Transactional
    @Modifying
    @Query(value="UPDATE user_request set request='ACCEPT'  where user_id=:userId AND friend_id=:friendId",
            nativeQuery = true)
    void acceptedRequest(@Param("userId") int userId,@Param("friendId") int friendId);

    @Transactional
    @Modifying
    @Query(value="DELETE  FROM user_request WHERE user_id =:userId AND friend_id=:friendId",
            nativeQuery = true)
    void rejectRequest(@Param("userId") int userId,@Param("friendId") int friendId);

    @Transactional
    @Modifying
    @Query(value="SELECT * FROM user,user_request WHERE user.id=user_request.user_id AND user_request.request='ACCEPT' AND user_request.friend_id=:userId",
            nativeQuery = true)
    List<User> friendsRequest(@Param("userId") int userId);


    User findByEmail(String email);

    List<User> findAllByNameContainsOrSurnameContains(String name, String surname);
}
