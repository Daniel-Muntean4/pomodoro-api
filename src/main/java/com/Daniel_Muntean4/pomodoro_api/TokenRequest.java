package com.Daniel_Muntean4.pomodoro_api;

import java.util.List;

public record TokenRequest(String subject, List<String> permissions) {

}
