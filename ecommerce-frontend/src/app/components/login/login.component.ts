import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user.model';
import { AuthService } from 'src/app/service/auth.service';
import { environment } from 'src/environments/environment.development';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = {
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    address: '',
    enabled: false,
    confirmationToken: ''
  };


  constructor(private auth: AuthService, private router: Router) {};
  ngOnInit(): void {} ;

  handleLogin() {
    this.auth.login(this.user)
    .subscribe(
      (response) => {
        console.log('Login successful:', response);
        this.router.navigateByUrl('[/home]')
        // Handle successful login (e.g., redirect to home page)
      },
      (error) => {
        console.error('Login failed:', error);
        // Handle login failure (e.g., show error message)
      }
    );;
  }
  
}