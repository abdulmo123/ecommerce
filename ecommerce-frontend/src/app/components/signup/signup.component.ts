import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user.model';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  user: User = {
    id: 0,
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    address: ''
  };
  signupFormSubmitted = false;
  @ViewChild('signupForm') signupForm: NgForm | undefined;


  constructor(private auth: AuthService, private router: Router) {}

  goToLogin() {
    this.router.navigate(['/login']);
  }

  handleSignUp() {
    // Set signupFormSubmitted to true to indicate that the form has been submitted
    this.signupFormSubmitted = true;

    if (this.signupForm!.valid) {
      // Call the AuthService's signup method to send the user data to the server
      this.auth.signup(this.user).subscribe(
        (response) => {
          console.log("successful signup!")
          this.router.navigate(['/login']);
        },
        (error) => {
          // Handle signup error (e.g., display an error message to the user)
          console.log("failed signup!");
        }
      );
    }
  }
}
