import org.junit.jupiter.api.Test;
import ru.geekbrains.library.dictionary.RoleEnum;
import ru.geekbrains.library.dto.RoleDto;

public class TestClass {
    @Test
    void test() {
        RoleEnum roleEnum = RoleEnum.valueOf("ROLE_ADMIN");
        System.out.println(roleEnum.getName());
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("ROLE_ADMIN");
        System.out.println(roleDto);
        System.out.println(roleDto.getName());
    }
}
