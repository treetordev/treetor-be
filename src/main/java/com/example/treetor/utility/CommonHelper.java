package com.example.treetor.utility;



import com.example.treetor.entity.Skills;
import com.example.treetor.entity.UserModel;
import com.example.treetor.response.UserDetailsUiResponse;

import java.util.*;

public class CommonHelper {

	public static Map<String, String> convertFromSoapElements(Map<String, List<String>> req) {
		Map<String, String> result = new HashMap<>();
		for (Map.Entry<String, List<String>> entry : req.entrySet()) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			String concatValue = String.join("|", values);
			result.put(key, concatValue);
		}
		return result;
	}

	public static String generateUserKey() {
		String uuid = java.util.UUID.randomUUID().toString();
		String formattedKey = uuid.substring(0, 8) + "-" + uuid.substring(8, 13) + "-" + uuid.substring(13);
		return formattedKey;
	}

	public static UserDetailsUiResponse convertToUserDetailsUiResponse(UserModel userByUserKey) {
		// TODO Auto-generated method stub
		UserDetailsUiResponse uiResponse = new UserDetailsUiResponse();
		uiResponse.setEmail(userByUserKey.getEmail());
		uiResponse.setName(userByUserKey.getName());
		uiResponse.setUserKey(userByUserKey.getUserKey());
		uiResponse.setRoles(userByUserKey.getRoles());
		uiResponse.setUserKey(userByUserKey.getUserKey());
		uiResponse.setUserDetailsId(userByUserKey.getUserDetailsId());
		Set<Skills> skills = userByUserKey.getSkills();
		Set<String> skillResponse = new HashSet<>();
		for(Skills skills1: skills){
			skillResponse.add(skills1.getName());
		}
		uiResponse.setSkills(skillResponse);
		return uiResponse;
	}
}
