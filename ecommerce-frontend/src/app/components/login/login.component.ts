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
    id: 0,
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    address: ''
  };


  constructor(private auth: AuthService, private router: Router) {};
  ngOnInit(): void {} ;

  handleLogin() {
    this.auth.login(this.user).subscribe(
      (response: Object) => {
        const userIdMatch = (response as string).match(/id=(\d+)/);

        if (userIdMatch && userIdMatch[1]) {
          const userId = userIdMatch[1];
          localStorage.setItem('userId', userId);
          console.log("success!");
          console.log("user id => ", userId);
          this.router.navigate(['/home']);
        }
        else {
          console.error("user id not found in the response!");
        }
      },
      (error: any) => {
        alert('Invalid username and/or password');
        console.log('error');
        console.error(error);
        console.log(this.user.email, ":", this.user.password);
        console.log("user id: " + this.user.id)
      }
    );
  }

  goToSignUp() {
    this.router.navigate(['/signup']);
  }
}
