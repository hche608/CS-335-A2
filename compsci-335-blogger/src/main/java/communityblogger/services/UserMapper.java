package communityblogger.services;

import communityblogger.domain.User;

public class UserMapper {

	static User toDomainModel(communityblogger.dto.User dtoUser) {
		User fullUser = new User(dtoUser.getUsername(), dtoUser.getLastname(),
				dtoUser.getFirstname());
		return fullUser;
	}

	static communityblogger.dto.User toDto(User _user) {
		communityblogger.dto.User dtoUser = new communityblogger.dto.User(
				_user.getUsername(), _user.getLastname(), _user.getFirstname());
		return dtoUser;
	}
}
