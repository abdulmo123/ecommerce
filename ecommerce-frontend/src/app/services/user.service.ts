import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    const body = {
      username: username,
      password: password
    };

    return this.http.post(environment.hostUrl + '/login', body).pipe(
      map((response:any) => {
        if (response && response.token) {
          localStorage.setItem('user', JSON.stringify(response));
        }

        return response;
      }),
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          return throwError('Invalid username or password');
        }

        return throwError('Something went wrong');
      })
    );
  }

  logout() {
    localStorage.removeItem('user');
  }
}
  // return this.http.get(environment.hostUrl,
    //   { headers: { Authorization: this.createBasicAuthToken(username, password) } }).pipe(map((res) => {
    //     this.username = username;
    //     this.password = password;
    //     this.registerSuccessfulLogin(username, password);
    //   }));

  // createBasicAuthToken(username: string, password: string) {
  //   return 'Basic' + window.btoa(username+":"+password);
  // }

  // registerSuccessfulLogin(username: string, password: string) {
  //   sessionStorage.setItem('username', username)
  // }

  // isUserLoggedIn() {
  //   let user = sessionStorage.getItem('username');
  //   if (user === null) {
  //     return false;
  //   }

  //   return true;
  // }

  // getLoggedInUsername() {
  //   let user = sessionStorage.getItem('username');
  //   if (user === null) {
  //     return '';
  //   }

  //   return user;
  // }
