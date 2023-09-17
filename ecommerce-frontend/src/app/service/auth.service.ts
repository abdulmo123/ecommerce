import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(user: User) {
    const headers = new HttpHeaders({
      Authorization: 'Basic ' +
      btoa(user.email + ':' + user.password)
    });
    return this.http.get(`${environment.hostUrl}/login`, { headers, responseType: 'text' as 'json' } )
  }

  signup(user: User) {
    return this.http.post(`${environment.hostUrl}/api/users/save`, user);
  }
}
