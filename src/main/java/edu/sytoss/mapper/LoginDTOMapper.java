package edu.sytoss.mapper;

import edu.sytoss.dto.CustomerSignUpDTO;
import edu.sytoss.dto.LoginDTO;
import edu.sytoss.model.user.UserAccount;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;


public class LoginDTOMapper {
    private ModelMapper modelMapper = new ModelMapper();

    PropertyMap<LoginDTO, UserAccount> propertyMap = new PropertyMap<LoginDTO, UserAccount>() {
        protected void configure() {
            map(source.getUsername(), destination.getLogin());
            map(source.getPassword(), destination.getPassword());
        }
    };

    public UserAccount map(CustomerSignUpDTO customerSingUpDTO) {
        modelMapper.addMappings(propertyMap);
        return modelMapper.map(customerSingUpDTO, UserAccount.class);
    }
}
