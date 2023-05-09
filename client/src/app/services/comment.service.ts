import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Comment } from '../models/Comment';

const API_RAILWAY = 'https://tiny-direction-production.up.railway.app/api/comment'
const API_URL = 'http://localhost:8080/api/comment'

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http : HttpClient) { }

  postComment(comment : Comment) {
    const c = new HttpParams()
    .set("name", comment.name)
    .set("title", comment.title)
    .set("rating", comment.rating)
    .set("comment", comment.comment)

  const headers = new HttpHeaders()
  .set("Content-Type", 'application/x-www-form-urlencoded')

  return firstValueFrom(this.http.post<any>(API_URL, c, {headers : headers}))
  }
}
