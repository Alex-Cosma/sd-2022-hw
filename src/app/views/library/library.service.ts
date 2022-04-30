import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RegisterUserService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public generatePdf(): void {
    const body = { title: 'Angular PUT Request Example' };
    this.http.put<any>(`${this.apiServerUrl}/books/pdf`,body)
  }
}
