package edu.java.scrapper.mapper;

import edu.java.scrapper.dto.api.response.LinkResponse;
import edu.java.scrapper.model.Link;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LinkMapper extends GenericMapper<Link, LinkResponse> {
    protected LinkMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected Class<LinkResponse> getDtoClass() {
        return LinkResponse.class;
    }
}
