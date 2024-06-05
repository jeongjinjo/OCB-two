package com.green.onezo.review;

import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.store.Store;
import com.green.onezo.store.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewDto getReview(ReviewDto reviewDto) {
        ModelMapper modelMapper = new ModelMapper();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Member> memberOptional = memberRepository.findByUserId(user.getUsername());
        Optional<Store> storeOptional = storeRepository.findById(reviewDto.getStoreId());

        if (memberOptional.isPresent() && storeOptional.isPresent()) {
            Member member = memberOptional.get();
            Store store = storeOptional.get();
            List<Review> reviewList = reviewRepository.findByMemberId(member.getId());
            if (reviewList.isEmpty()) {
                Review review = modelMapper.map(reviewDto, Review.class);
                review.setMember(member);
                review.setStore(store);
                review = reviewRepository.save(review);

                return modelMapper.map(review, ReviewDto.class);

            } else {
                Review revdto = reviewList.get(0);
                revdto.setStore(store);
                revdto.setStar(reviewDto.getStar());
                revdto.setComment(reviewDto.getComment());
                revdto = reviewRepository.save(revdto);
                return modelMapper.map(revdto, ReviewDto.class);

            }
        }
        return reviewDto;
    }
}

