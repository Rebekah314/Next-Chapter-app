package org.launchcode.nextchapter.controllers;



import org.launchcode.nextchapter.models.MemberProfile;
import org.launchcode.nextchapter.data.MemberProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
class MemberProfileController {

    private final MemberProfileRepository profileRepository;

    @Autowired
    public MemberProfileController(MemberProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public List<MemberProfile> getAllProfiles() {
        return (List<MemberProfile>) profileRepository.findAll();
    }

    @GetMapping("/{id}")
    public MemberProfile getProfileById(@PathVariable Long id) {
        return profileRepository.findById( Math.toIntExact( id ) )
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @PostMapping
    public MemberProfile createProfile(@RequestBody MemberProfile profile) {
        return profileRepository.save(profile);
    }

    @PutMapping("/{id}")
    public MemberProfile updateProfile(@PathVariable Long id, @RequestBody MemberProfile updatedProfile) {
        MemberProfile existingProfile = profileRepository.findById( Math.toIntExact( id ) )
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        existingProfile.setName(updatedProfile.getName());
        existingProfile.setEmail(updatedProfile.getEmail());
        existingProfile.setRole(updatedProfile.getRole());

        return profileRepository.save(existingProfile);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileRepository.deleteById( Math.toIntExact( id ) );
    }
}
