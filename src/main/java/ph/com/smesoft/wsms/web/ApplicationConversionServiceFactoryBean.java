package ph.com.smesoft.wsms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ph.com.smesoft.wsms.domain.CustomerType;
import ph.com.smesoft.wsms.domain.Floor;
import ph.com.smesoft.wsms.domain.IndustryType;
import ph.com.smesoft.wsms.domain.LocationType;
import ph.com.smesoft.wsms.service.CustomerTypeService;
import ph.com.smesoft.wsms.service.FloorService;
import ph.com.smesoft.wsms.service.IndustryTypeService;
import ph.com.smesoft.wsms.service.LocationTypeService;



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
    LocationTypeService AreaService;

	public Converter<LocationType, String> getAreaToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ph.com.smesoft.wsms.domain.LocationType, java.lang.String>() {
            public String convert(LocationType LocationType) {
                return new StringBuilder().append(LocationType.getLocationTypeName()).toString();
            }
        };
    }

	public Converter<Long, LocationType> getIdToAreaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ph.com.smesoft.wsms.domain.LocationType>() {
            public ph.com.smesoft.wsms.domain.LocationType convert(java.lang.Long id) {
                return LocationTypeService.findLocationType(id);
            }
        };
    }

	public Converter<String, LocationType> getStringToAreaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ph.com.smesoft.wsms.domain.LocationType>() {
            public ph.com.smesoft.wsms.domain.LocationType convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), LocationType.class);
            }
        };
    }	

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getFloorToStringConverter());
        registry.addConverter(getIdToFloorConverter());
        registry.addConverter(getStringToFloorConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
