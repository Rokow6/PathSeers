package edu.utsa.cs3773.pathseer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AccountSetting extends NavigationActivity {

    private TextInputEditText currentPasswordInput, newPasswordInput, confirmNewPasswordInput;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting); // Use the correct layout file

        // Initialize views
        currentPasswordInput = findViewById(R.id.current_password);
        newPasswordInput = findViewById(R.id.new_password);
        confirmNewPasswordInput = findViewById(R.id.confirm_new_password);
        changePasswordButton = findViewById(R.id.change_password_button);

        // Set button click listener to change password
        changePasswordButton.setOnClickListener(v -> changePassword());

        // Add a listener to validate new password strength dynamically
        newPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                validatePasswordStrength(newPasswordInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    // Method to change the password
    private void changePassword() {
        String currentPassword = currentPasswordInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmNewPasswordInput.getText().toString().trim();

        // Validate the inputs
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 8) {
            Toast.makeText(this, "New password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulate password change success
        Toast.makeText(this, "Password successfully changed", Toast.LENGTH_SHORT).show();
    }

    // Method to validate password strength
    private void validatePasswordStrength(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar) {
            newPasswordInput.setError("Password must contain at least 1 uppercase, 1 lowercase, 1 digit, and 1 special character.");
        }
    }
}
