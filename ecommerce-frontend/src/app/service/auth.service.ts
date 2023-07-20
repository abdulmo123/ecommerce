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
  
  login(user: User) : Observable<string> {
    const headers = new HttpHeaders()
    .set('Content-Type', 'application/x-www-form-urlencoded');

    const body = new URLSearchParams();
    body.set('username', user.email)
    body.set('password', user.password);

    return this.http.post(`${environment.hostUrl}/login`, body.toString(), { responseType: 'text', headers } );
  }  
}