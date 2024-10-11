package ojosama.talkak.member.domain;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Age {
    TEN("10대"), TWENTY("20대"), THIRTY("30대"), FORTY("40대"), FIFTY("50대 이상");

    private final String name;

    public static Age fromName(String name) {
        return Arrays.stream(Age.values())
            .filter(age -> age.getName().equals(name))
            .findFirst()
            .orElse(null);
    }
}
