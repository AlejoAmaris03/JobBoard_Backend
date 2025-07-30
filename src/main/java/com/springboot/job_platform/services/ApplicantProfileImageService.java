package com.springboot.job_platform.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.job_platform.dto.ApplicantProfileImageDTO;
import com.springboot.job_platform.models.ApplicantProfileImage;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.ApplicantProfileImageRepository;
import com.springboot.job_platform.repositories.UserRepository;

@Service

public class ApplicantProfileImageService {
    @Autowired
    private ApplicantProfileImageRepository applicantProfileImageRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public Map<String, Object> getProfileImageByApplicantId(int id) throws Exception {
        try {
            ApplicantProfileImage profileImage = applicantProfileImageRepo.findProfileImageByApplicantId(id);

            if(profileImage == null) {
                return Map.of(
                    "message", "No profile image found",
                    "profileImage", ""
                );
            }

            return Map.of(
                "message", "Profile image fetched successfully",
                "profileImage", getProfileImageDTO(profileImage)
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching profile image: " + e.getMessage());
        }
    }

    @SuppressWarnings("null")
    @Transactional
    public Map<String, Object> saveProfileImage(User applicant, MultipartFile profileImage) throws Exception {
        try {
            List<String> allowedFileTypes = List.of("png", "jpeg", "webp", "svg");
            User applicantRetrieved = userRepo.findById(applicant.getId()).orElse(null);

            if(applicantRetrieved == null)
                throw new Exception("Applicant not found");

            if(!allowedFileTypes.contains(profileImage.getContentType().split("/")[1]))
                throw new Exception("Picture format not valid");

            ApplicantProfileImage image = applicantProfileImageRepo.findProfileImageByApplicantId(applicant.getId());

            if(image == null) {
                applicantProfileImageRepo.save(new ApplicantProfileImage(
                    0,
                    profileImage.getOriginalFilename(),
                    profileImage.getContentType(),
                    profileImage.getBytes(),
                    applicant
                ));
            }
            else {
                image.setProfileImageData(profileImage.getBytes());
                image.setProfileImageName(profileImage.getOriginalFilename());
                image.setProfileImageType(profileImage.getContentType());

                applicantProfileImageRepo.save(image);
            }

            return Map.of(
                "message", "Profile image updated successfully",
                "imageName", profileImage.getOriginalFilename()
            );
        } 
        catch (Exception e) {
            throw new Exception("Error saving profile image: " + e.getMessage());
        }
    }

    private ApplicantProfileImageDTO getProfileImageDTO(ApplicantProfileImage profileImage) {
        return new ApplicantProfileImageDTO(
            profileImage.getId(),
            profileImage.getApplicant().getName() + " " + profileImage.getApplicant().getSurname(),
            profileImage.getProfileImageName(),
            profileImage.getProfileImageType(),
            profileImage.getProfileImageData()
        );
    }
}
