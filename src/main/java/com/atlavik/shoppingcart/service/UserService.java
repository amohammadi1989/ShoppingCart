package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.UserDto;
import com.atlavik.shoppingcart.model.User;
import com.atlavik.shoppingcart.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService  implements UserDetailsService {

        private final UserRepository userRepository;
        private ModelMapper modelMapper;
        private  final BCryptPasswordEncoder bCryptPasswordEncoder ;

        public UserService(UserRepository userRepository,
		       ModelMapper modelMapper
        ) {
	      this.userRepository = userRepository;
	      this.modelMapper = modelMapper;
	      this.bCryptPasswordEncoder =new BCryptPasswordEncoder(  );
        }

        public Optional<UserDto> findUserByUserName(String userName){
	      Optional<User> user=userRepository.findUserByUserName(userName);
	      return user.map( value -> modelMapper.map( value, UserDto.class ) );
        }

        public UserDto saveUser(UserDto userDto){
	      userDto.setPassword( bCryptPasswordEncoder.encode( userDto.getPassword() ));
	      return  modelMapper.map(
		    userRepository.save(
			  modelMapper.map( userDto ,User.class ))
		    ,UserDto.class);
        }
        @Override
        public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	      Optional<UserDto>  findUser=findUserByUserName( userName );
	      if(!findUser.isPresent())
		    throw new UsernameNotFoundException( UserConstants.USER_INVALID );
	      return org.springframework.security.core.userdetails.User
		    .withUsername( userName )
		    .password( findUser.get().getPassword() )
		    .roles( UserConstants.USER_WRITE_PRIVILEGE )
		    .accountExpired( false )
		    .accountLocked( false )
		    .credentialsExpired( false )
		    .disabled( false )
		    .build();
        }
}
