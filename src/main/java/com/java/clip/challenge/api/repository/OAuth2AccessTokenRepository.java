package com.java.clip.challenge.api.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.clip.challenge.api.model.OAuth2AuthenticationAccessToken;

@Repository
public interface OAuth2AccessTokenRepository extends MongoRepository<OAuth2AuthenticationAccessToken, Serializable> {

	@Query(value = "{'authenticationId': ?0}")
	boolean existsByAuthenticationId(String authId);

	boolean existsByTokenId(String tokenId);

	void deleteByTokenId(String tokenId);

}
