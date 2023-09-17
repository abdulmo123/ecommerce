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
    this.signupFormSubmitted = true;

    if (this.signupForm!.valid) {
      this.auth.signup(this.user).subscribe(
        (response) => {
          console.log("successful signup!")
          this.router.navigate(['/login']);
        },
        (error) => {
          console.log("failed signup!");
        }
      );
    }
  }
}
