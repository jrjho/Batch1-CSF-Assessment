import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Comment } from 'src/app/models/Comment';
import { CommentService } from 'src/app/services/comment.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit,OnDestroy {


  movieTitleToComment$!: Subscription
  inputtedMovieName$!: Subscription
  title!: string
  form!: FormGroup
  comment!: Comment
  query!: string

  constructor(private router : Router, 
    private activatedRoute : ActivatedRoute,
    private commentSvc : CommentService,
    private fb: FormBuilder) {}

  ngOnInit(): void {

    //get title to insert comment
    this.movieTitleToComment$ = this.activatedRoute.params.subscribe(
      (param) => {
        this.title = param['title']
        console.info("title: ",this.title)
      }
    )

    //used to load search page again with inputted movie name after clicking back button
    this.inputtedMovieName$ = this.activatedRoute.queryParams.subscribe(
      (param) => {
        this.query = param['query']
        console.info("query: ",this.query)
      }
    )

    this.form = this.fb.group({
      name: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      rating: this.fb.control<number>(3, [Validators.required, Validators.max(5), Validators.min(1)]),
      comment: this.fb.control<string>("", [Validators.required])
    })
  }

  submitComment() {

    this.comment = {
      name: this.form.value['name'],
      title: this.title,
      rating: this.form.value['rating'],
      comment: this.form.value['comment']
    }

    console.info(this.comment)

    this.commentSvc.postComment(this.comment).then(
      (data) => console.info(data['response'])
    )
    this.router.navigate(["/search"], {queryParams: {query: this.query}})

  }

  goback() {
    this.form.reset()
    this.router.navigate(["/search"], {queryParams: {query: this.query}})
  }
  ngOnDestroy(): void {
    this.movieTitleToComment$.unsubscribe()
    this.inputtedMovieName$.unsubscribe()
  }
}
