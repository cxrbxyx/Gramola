import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  // Asegúrate de que este puerto coincide con el de tu Backend (Spring Boot)
  private apiUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }

  register(bar: string, email: string, pwd1: string, pwd2: string, clientId: string, clientSecret: string): Observable<any> {
    const body = {
      bar: bar,
      email: email,
      pwd1: pwd1,
      pwd2: pwd2,
      clientId: clientId,
      clientSecret: clientSecret
    };

    // Enviamos una petición POST al backend.
    // Como tu backend devuelve ResponseEntity<Void> (vacío), no necesitamos configurar responseType: 'text'
    // a menos que cambies el backend para devolver un String.
    return this.http.post(`${this.apiUrl}/register`, body);
  }
}