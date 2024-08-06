package mate.academy.springintro.mapper;

import java.util.List;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.BookRequestDto;
import mate.academy.springintro.dto.BookResponseDto;
import mate.academy.springintro.model.CreatedBookDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    BookDto toBookDto(CreatedBookDto book);

    List<BookDto> toBookDto(List<CreatedBookDto> book);

    CreatedBookDto toModel(BookRequestDto requestDto);

    void updateBookFromDto(BookResponseDto book, @MappingTarget CreatedBookDto entity);
}


