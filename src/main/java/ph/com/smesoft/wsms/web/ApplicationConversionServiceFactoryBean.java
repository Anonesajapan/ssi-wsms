package ph.com.smesoft.wsms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ph.com.smesoft.wsms.domain.Contact;
import ph.com.smesoft.wsms.service.ContactService;
import ph.com.smesoft.wsms.domain.Customer;
import ph.com.smesoft.wsms.service.CustomerService;
import ph.com.smesoft.wsms.domain.CustomerType;
import ph.com.smesoft.wsms.domain.Floor;
import ph.com.smesoft.wsms.domain.IndustryType;
import ph.com.smesoft.wsms.domain.LocationType;
import ph.com.smesoft.wsms.domain.Barangay;
import ph.com.smesoft.wsms.service.BarangayService;
import ph.com.smesoft.wsms.domain.Street;
import ph.com.smesoft.wsms.service.StreetService;
import ph.com.smesoft.wsms.domain.City;
import ph.com.smesoft.wsms.service.CityService;
import ph.com.smesoft.wsms.domain.Area;

import ph.com.smesoft.wsms.service.CustomerTypeService;
import ph.com.smesoft.wsms.service.FloorService;
import ph.com.smesoft.wsms.service.IndustryTypeService;
import ph.com.smesoft.wsms.service.LocationTypeService;
import ph.com.smesoft.wsms.service.AreaService;



@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    FloorService floorService;


	public Converter<Floor, String> getFloorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.Floor, java.lang.String>() {
            public String convert(Floor floor) {
                return new StringBuilder().append(floor.getFloorNumber()).append(' ').append(floor.getDescription()).toString();
            }
        };
    }

	public Converter<Long, Floor> getIdToFloorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.Floor>() {
            public ph.com.smesoft.wsms.domain.Floor convert(java.lang.Long id) {
                return floorService.findFloor(id);
            }
        };
    }

	public Converter<String, Floor> getStringToFloorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.Floor>() {
            public ph.com.smesoft.wsms.domain.Floor convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Floor.class);
            }
        };
    }
	@Autowired
    BarangayService barangayService;

	public Converter<Barangay, String> getBarangayToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.Barangay, java.lang.String>() {
            public String convert(Barangay barangay) {
                return new StringBuilder().append(barangay.getBarangayName()).toString();
            }
        };
    }

	public Converter<Long, Barangay> getIdToBarangayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.Barangay>() {
            public ph.com.smesoft.wsms.domain.Barangay convert(java.lang.Long id) {
                return barangayService.findBarangay(id);
            }
        };
    }

	public Converter<String, Barangay> getStringToBarangayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.Barangay>() {
            public ph.com.smesoft.wsms.domain.Barangay convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Barangay.class);
            }
        };
    }
	
	@Autowired
    CustomerTypeService customerTypeService;

	public Converter<CustomerType, String> getCustomertypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.CustomerType, java.lang.String>() {
            public String convert(CustomerType customerType) {
                return new StringBuilder().append(customerType.getCustomerTypeName()).toString();
            }
        };
    }

	public Converter<Long, CustomerType> getIdToCustomertypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.CustomerType>() {
            public ph.com.smesoft.wsms.domain.CustomerType convert(java.lang.Long id) {
                return customerTypeService.findCustomerType(id);
            }
        };
    }

	public Converter<String, CustomerType> getStringToCustomertypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.CustomerType>() {
            public ph.com.smesoft.wsms.domain.CustomerType convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CustomerType.class);
            }
        };
    }

	@Autowired
    IndustryTypeService industryTypeService;

	public Converter<IndustryType, String> getIndustrytypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.IndustryType, java.lang.String>() {
            public String convert(IndustryType industryType) {
                return new StringBuilder().append(industryType.getIndustryTypeName()).toString();
            }
        };
    }

	public Converter<Long, IndustryType> getIdToIndustrytypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.IndustryType>() {
            public ph.com.smesoft.wsms.domain.IndustryType convert(java.lang.Long id) {
                return industryTypeService.findIndustrytype(id);
            }
        };
    }

	public Converter<String, IndustryType> getStringToIndustrytypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.IndustryType>() {
            public ph.com.smesoft.wsms.domain.IndustryType convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), IndustryType.class);
            }
        };
    }
	
	
	@Autowired
    CustomerService customerService;

	public Converter<Customer, String> getCustomerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.Customer, java.lang.String>() {
            public String convert(Customer customer) {
                return new StringBuilder().append(customer.getCustomerName()).toString();
            }
        };
    }

	public Converter<Long, Customer> getIdToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.Customer>() {
            public ph.com.smesoft.wsms.domain.Customer convert(java.lang.Long id) {
                return customerService.findCustomer(id);
            }
        };
    }
	
	public Converter<String, Customer> getStringToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.Customer>() {
            public ph.com.smesoft.wsms.domain.Customer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Customer.class);
            }
        };
    }
	
	@Autowired
    ContactService contactService;

	public Converter<Contact, String> getContactToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.Contact, java.lang.String>() {
            public String convert(Contact contact) {
                return new StringBuilder().append(contact.getFirstName()).append(contact.getMiddleName()).append(contact.getLastName()).toString();
            }
        };
    }

	public Converter<Long, Contact> getIdToContactConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.Contact>() {
            public ph.com.smesoft.wsms.domain.Contact convert(java.lang.Long id) {
                return contactService.findContact(id);
            }
        };
    }
	
	public Converter<String, Contact> getStringToContactConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.Contact>() {
            public ph.com.smesoft.wsms.domain.Contact convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Contact.class);
            }
        };
    }
	
	@Autowired
    CityService cityService;

	public Converter<City, String> getCityToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.City, java.lang.String>() {
            public String convert(City city) {
                return new StringBuilder().append(city.getCityName()).toString();
            }
        };
    }

	public Converter<Long, City> getIdToCityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.City>() {
            public ph.com.smesoft.wsms.domain.City convert(java.lang.Long id) {
                return cityService.findCity(id);
            }
        };
    }

	public Converter<String, City> getStringToCityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.City>() {
            public ph.com.smesoft.wsms.domain.City convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), City.class);
            }
        };
    }

	
	@Autowired
    LocationTypeService LocationTypeService;

	public Converter<LocationType, String> getLocationtypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.LocationType, java.lang.String>() {
            public String convert(LocationType LocationType) {
                return new StringBuilder().append(LocationType.getLocationTypeName()).toString();
            }
        };
    }

	public Converter<Long, LocationType> getIdToLocationtypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.LocationType>() {
            public ph.com.smesoft.wsms.domain.LocationType convert(java.lang.Long id) {
                return LocationTypeService.findLocationType(id);
            }
        };
    }

	public Converter<String, LocationType> getStringToLocationtypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.LocationType>() {
            public ph.com.smesoft.wsms.domain.LocationType convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), LocationType.class);
            }
        };
    }	

	@Autowired
    AreaService areaService;

	public Converter<Area, String> getAreaToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.Area, java.lang.String>() {
            public String convert(Area area) {
                return new StringBuilder().append(area.getAreaName()).toString();
            }
        };
    }

	public Converter<Long, Area> getIdToAreaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.Area>() {
            public ph.com.smesoft.wsms.domain.Area convert(java.lang.Long id) {
                return Area.findArea(id);
            }
        };
    }

	public Converter<String, Area> getStringToAreaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.Area>() {
            public ph.com.smesoft.wsms.domain.Area convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Area.class);
            }
        };
    }	
	
	@Autowired
    StreetService streetService;

	public Converter<Street, String> getStreetToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.Street, java.lang.String>() {
            public String convert(Street street) {
                return new StringBuilder().append(street.getStreetName()).toString();
            }
        };
    }

	public Converter<Long, Street> getIdToStreetConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.Street>() {
            public ph.com.smesoft.wsms.domain.Street convert(java.lang.Long id) {
                return streetService.findStreet(id);
            }
        };
    }

	public Converter<String, Customer> getStringToStreetConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.Customer>() {
            public ph.com.smesoft.wsms.domain.Customer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Customer.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getFloorToStringConverter());
        registry.addConverter(getIdToFloorConverter());
        registry.addConverter(getStringToFloorConverter());
       
        registry.addConverter(getIndustrytypeToStringConverter());
        registry.addConverter(getIdToIndustrytypeConverter());
        registry.addConverter(getStringToIndustrytypeConverter());
       
        registry.addConverter(getLocationtypeToStringConverter());
        registry.addConverter(getIdToLocationtypeConverter());
        registry.addConverter(getStringToLocationtypeConverter());
       
        registry.addConverter(getCustomertypeToStringConverter());
        registry.addConverter(getIdToCustomertypeConverter());
        registry.addConverter(getStringToCustomertypeConverter());
        
        registry.addConverter(getAreaToStringConverter());
        registry.addConverter(getIdToAreaConverter());
        registry.addConverter(getStringToAreaConverter());
        
        registry.addConverter(getCityToStringConverter());
        registry.addConverter(getIdToCityConverter());
        registry.addConverter(getStringToCityConverter());
        
        
        
        registry.addConverter(getBarangayToStringConverter());
        registry.addConverter(getIdToBarangayConverter());
        registry.addConverter(getStringToBarangayConverter());
        
        registry.addConverter(getStreetToStringConverter());
        registry.addConverter(getIdToStreetConverter());
        registry.addConverter(getStringToStreetConverter());
        
        
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
