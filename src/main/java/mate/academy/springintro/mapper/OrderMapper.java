package mate.academy.springintro.mapper;

import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.order.OrderResponseDto;
import mate.academy.springintro.dto.order.UpdateOrderStatusRequest;
import mate.academy.springintro.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toDto(Order order);

    void update(@MappingTarget Order order, UpdateOrderStatusRequest updateRequest);
}
