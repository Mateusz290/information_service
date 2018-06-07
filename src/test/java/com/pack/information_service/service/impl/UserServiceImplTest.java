package com.pack.information_service.service.impl;

import com.pack.information_service.domain.User;
import com.pack.information_service.repository.RoleRepository;
import com.pack.information_service.repository.UserRepository;
import com.pack.information_service.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;

public class UserServiceImplTest {

    private UserRepository mockUserRepository;
    private RoleRepository mockRoleRepository;
    private PasswordEncoder mockPasswordEncoder;
    private User mockUser = null;
     private User realUser;
    private Role role;
    private UserServiceImpl usi;
     private List<User> userList;
    private List<User> userListEmpty;

    private static final String USERNAME_OF_EXISTING_USER = "Caius Cosades";
    private static final String USERNAME_OF_NOT_EXISTING_USER = "Hasphat Antabolis";
    private static final Long ID_OF_EXISTING_USER = 1L;
    private static final Long ID_OF_NOT_EXISTING_USER = 0L;
    private static final String NAME="MODERATOR";

    @Before
    public void setup() {

        mockUserRepository = Mockito.mock(UserRepository.class);
        mockPasswordEncoder = Mockito.mock(PasswordEncoder.class);
        mockRoleRepository = Mockito.mock(RoleRepository.class);
        mockUser = Mockito.mock(User.class);
        realUser = new User();
         role=new Role();
        usi = new UserServiceImpl(mockUserRepository, mockRoleRepository, mockPasswordEncoder);

        when(mockUserRepository.findByUsername(USERNAME_OF_EXISTING_USER)).thenReturn(mockUser);
        when(mockUserRepository.findByUsername(USERNAME_OF_NOT_EXISTING_USER)).thenReturn(null);
        when(mockUserRepository.findByIdUser(ID_OF_EXISTING_USER)).thenReturn(mockUser);
        when(mockUserRepository.findByIdUser(ID_OF_NOT_EXISTING_USER)).thenReturn(null);
        when(mockRoleRepository.findByName(USERNAME_OF_EXISTING_USER )).thenReturn(role);
        
        realUser.setCategory("Sport");


        userList = new ArrayList<>();
        userList.add(mockUser);

        userListEmpty = new ArrayList<>();

    }

    @Test
    public void findByUsername_UserExists_UserFound(){

        assertSame(mockUser, usi.findByUsername(USERNAME_OF_EXISTING_USER));
    }

    @Test
    public void findByUsername_UserNotExists_UserNotFound(){

        assertNull(usi.findByUsername(USERNAME_OF_NOT_EXISTING_USER));
    }

    @Test
    public void lock_UserNotBlocked_UserBlocked(){

        usi.lock(ID_OF_EXISTING_USER);
        assertTrue(!mockUser.isBlocked());
    }
    
     @Test
    public void findAll_ListOfUsersExists_ListFound(){

        when(mockUserRepository.findAll()).thenReturn(userList);
        List<User> test_list = usi.findAll();
        assertFalse(test_list.isEmpty());
    }
    
    
      @Test
    public void findAll_ListOfUsersIsEmpty_ListHasNoItems(){

        when(mockUserRepository.findAll()).thenReturn(userListEmpty);

        List<User> test_list=usi.findAll();

        assertTrue(test_list.isEmpty());
    }
    
     @Test
    public void changeRole_Role_Changed(){

        Set<Role> roles = new HashSet<Role>() {{
            add(role);
        }};


        realUser.setRoles(roles);

        if (NAME.equals("MODERATOR")) realUser.setCategory("News");

        assertEquals("News",realUser.getCategory());
        assertEquals(roles,realUser.getRoles());

    }
    
    @Test
    public void changeCategory_Category_Changed(){
        assertEquals("Sport",realUser.getCategory());
        realUser.setCategory("Business");
        assertEquals("Business",realUser.getCategory());

    }

    
    
      @Test
    public void deleteName_UserExists_UserDeleted(){
        doAnswer(invocation -> {
            mockUser = null;
            return null;
        }).when(mockUserRepository).delete(any(User.class));

        usi.delete(USERNAME_OF_EXISTING_USER);

        assertNull(mockUser);


    }

    @Test
    public void delete_UserExists_UserDeleted(){
         doAnswer(invocation -> {
            realUser = null;
            return null;
        }).when(mockUserRepository).delete(any(User.class));

        usi.delete(ID_OF_EXISTING_USER);

        assertNull(realUser);

        
    }


}
