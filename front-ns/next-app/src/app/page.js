"use client";

import { SpaceBetween } from '@cloudscape-design/components';
import PostForm from '../components/PostForm'; import PostList from '../components/PostList';

export default function HomePage() {

  return (
    <div className="container posts mt-0">
      <SpaceBetween size="l">
        <PostForm />
        <PostList />
      </SpaceBetween>
    </div>
  );
}
