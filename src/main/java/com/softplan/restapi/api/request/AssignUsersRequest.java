package com.softplan.restapi.api.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignUsersRequest {

	List<String> userIds;
}
