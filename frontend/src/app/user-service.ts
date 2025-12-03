import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  // URL base del backend
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  register(user: any): Observable<any> {
    // Usamos el endpoint específico de registro que envía el mail
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  // Nuevo método para verificar la cuenta
  verifyAccount(code: string): Observable<string> {
    // Configuramos el parámetro 'code' para la petición GET
    const params = new HttpParams().set('code', code);
    
    // Esperamos una respuesta de texto plano (según el controlador Java)
    return this.http.get(`${this.apiUrl}/verify`, { params, responseType: 'text' });
  }
}