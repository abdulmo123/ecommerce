import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }
  
  login(user: User) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<any>(`${environment.hostUrl}/login`, user, { headers } ).pipe(
      map(response => {
        if (response.message === 'Login successful') {
          console.log('success!');
          // Handle successful login
        }
        else {
          console.log('error!');
          // do something else
        }
      }),
      catchError(error => {
        // Handle error response here
        return throwError(error);
      })
    );
  }
}