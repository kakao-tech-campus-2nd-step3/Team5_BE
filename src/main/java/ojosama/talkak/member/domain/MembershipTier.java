package ojosama.talkak.member.domain;

public enum MembershipTier {
    Basic, Standard, Premium;

    public static MembershipTier fromInt(int tier) {
        return switch (tier) {
            case 0 -> Basic;
            case 1 -> Standard;
            case 2 -> Premium;
            default -> throw new IllegalArgumentException("Invalid tier: " + tier);
        };
    }

    public int toInt() {
        return switch (this) {
            case Basic -> 0;
            case Standard -> 1;
            case Premium -> 2;
            default -> throw new IllegalArgumentException("Invalid tier");
        };
    }
}
