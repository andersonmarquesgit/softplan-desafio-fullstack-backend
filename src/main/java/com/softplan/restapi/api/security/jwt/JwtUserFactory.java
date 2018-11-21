package com.softplan.restapi.api.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.softplan.restapi.api.entity.User;
import com.softplan.restapi.api.enums.ProfileEnum;

/**
 * Classe responsável pela conversão de {@link User}, nosso usuário, no usuário que o Spring Security conhece {@link JwtUser}
 * @author Anderson Marques
 *
 */
public class JwtUserFactory {

	public JwtUserFactory() {
	}

	/**
	 * Gera um JwtUser com base nos dados do Usuário
	 * @param user
	 * @return
	 */
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(),
				mapToGrantedAuthorities(user.getProfile()));
	}

	/**
	 * Converte o perfil do usuário para o formato utilizado pelo Spring Security
	 * @param profileEnum
	 * @return
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
		return authorities;
	}
}
