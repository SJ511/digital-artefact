import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerTest {

    AccountManager accountManager = new AccountManager();

    @Test
    public void falseValidatePassword() {
        assertFalse(accountManager.validatePassword("687"));
        assertFalse(accountManager.validatePassword("!!"));
        assertFalse(accountManager.validatePassword("ADGSGDSG$$"));
    }

    @Test
    public void trueValidatePassword() {

    }

}