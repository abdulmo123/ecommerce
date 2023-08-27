import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { User } from '../model/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  private apiServerUrl = environment.hostUrl;

  public getAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.apiServerUrl}/api/users/all`);
  }
}
