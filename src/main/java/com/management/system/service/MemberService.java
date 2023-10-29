package com.management.system.service;

import com.management.system.entities.Member;
import com.management.system.exception.AlreadyExistsException;
import com.management.system.exception.PasswordsNotMatchException;

public interface MemberService {
    void register(Member content) throws AlreadyExistsException, PasswordsNotMatchException;
    void edit(Member content);
    Member findByEmail(String username);
}
