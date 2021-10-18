package edu.sytoss.mapper;

import edu.sytoss.dto.CustomerSignUpDTO;
import edu.sytoss.dto.LoginDTO;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.model.user.UserAccountRole;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.Date;

public class CustomerSingUpDTOMapper {
    private ModelMapper modelMapper = new ModelMapper();

    PropertyMap<CustomerSignUpDTO, UserAccount> propertyMap = new PropertyMap<CustomerSignUpDTO, UserAccount>() {
        protected void configure() {
            map(source.getName(), destination.getName());
            map(source.getSurname(), destination.getSurname());
            map(source.getUsername(), destination.getLogin());
            map(source.getPassword(), destination.getPassword());
            map(new Date(System.currentTimeMillis()), destination.getRegistrationDate());
            map(new Date(System.currentTimeMillis()), destination.getLastActivityDate());
            map(UserAccountRole.CUSTOMER.getUserAccountRole(), destination.getRole());
        }
    };

    public UserAccount map(CustomerSignUpDTO customerSingUpDTO) {
        modelMapper.addMappings(propertyMap);
        return modelMapper.map(customerSingUpDTO, UserAccount.class);
    }
}
