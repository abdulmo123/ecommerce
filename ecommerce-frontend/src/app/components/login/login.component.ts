import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  username!: string;
  password!: string;
  errorMessage = "Invalid username or password.";
  successMessage = "Logged in successfully!";
  invalidLogin = false;
  loginSuccess = false;


  constructor(private user: UserService, private router: Router) {};
  ngOnInit(): void {} ;

  handleLogin() {
    console.log(`username: ${this.username}, password: ${this.password}`)
    const loginData = {
      username: this.username,
      password: this.password
    }

    this.user.login(this.username, this.password)
    .subscribe(
      (response) => {
        // handle successful login
        this.invalidLogin = false;
        this.loginSuccess = true;
        console.log(this.successMessage);
        // redirect to main page
        this.router.navigate(["/home"]);
      },
      (error) => {
        // handle failed login
        this.invalidLogin = true;
        this.loginSuccess = false;
        this.errorMessage = error;
        console.log(error);
      }
    );
  }
}
  // this.user.login(this.username, this.password).subscribe((result) => {
    //   this.invalidLogin = false;
    //   this.loginSuccess = true;
    //   console.log(this.successMessage);
    //   // redirect to main page
    //   this.router.navigate(["/home"]);
    // }, () => {
    //   this.invalidLogin = true;
    //   this.loginSuccess = false;
    //   console.log(this.errorMessage);
    // });
  // handleLogin() {
  //   let response = this.auth.login(this.username, this.password);
  //   response.subscribe(data => {
  //     this.message = data;
  //     this.router.navigate(["/home"]);
  //     console.log("success!");
  //   })
  // }

