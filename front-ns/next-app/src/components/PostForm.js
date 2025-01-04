"use client";

import { Button, Container, DatePicker, FileInput, Form, FormField, Grid, Header, Input, SpaceBetween, Table, Textarea } from '@cloudscape-design/components';
import axios from 'axios';
import React from 'react';

export default function PostForm() {
    const [inputDate, setInputDate] = React.useState();
    const [inputImageFile, setInputImageFile] = React.useState([]);
    const [inputTitle, setInputTitle] = React.useState();
    const [inputContent, setInputContent] = React.useState();
    const [inputItem, setInputItem] = React.useState();
    const [isPostLoading, setIsPostLoading] = React.useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        setIsPostLoading(true);

        if (!inputTitle || !inputContent || !inputDate || !inputItem) {
            alert('제목, 내용, 날짜, 물품 이름을 모두 입력해주세요.');
            return;
        }

        const formData = new FormData();
        formData.append('title', inputTitle);
        formData.append('content', inputContent);
        formData.append('purchaseDate', inputDate);
        formData.append('item', inputItem);
        formData.append('images', inputImageFile[0]);

        axios.post(`/api/post`, formData)
            .then(response => {
                console.log(response);
                alert('등록 완료');
                window.location.reload();
            })
            .catch(err => {
                alert('등록 실패!');
                console.error('Error:', err);
                setIsPostLoading(false);
            })
            .finally(() => {
                setIsPostLoading(false);
            });
    };

    return (
        <form onSubmit={handleSubmit} encType="multipart/form-data">
            <Container
                header={
                    <Header variant="h1">중고 물품 등록</Header>
                }
            >
                <SpaceBetween size="s">
                    <Grid
                        gridDefinition={[
                            { colspan: { default: 4 } },
                            { colspan: { default: 6 } },
                        ]}
                    >
                        <FormField label="물품명" description="판매할 중고 물품을 입력해주세요">
                            <Input
                                value={inputItem}
                                onChange={({ detail }) => setInputItem(detail.value)}
                            />
                        </FormField>

                        <FormField label="구매일" description="중고 물품의 구매일을 선택해주세요">
                            <DatePicker
                                onChange={({ detail }) => setInputDate(detail.value)}
                                value={inputDate}
                                placeholder="YYYY/MM/DD"
                            />
                        </FormField>
                    </Grid>

                    <FormField label="사진" description="판매할 중고 물품 사진을 선택해주세요">
                        <FileInput
                            value={inputImageFile}
                            onChange={({ detail }) => setInputImageFile(detail.value)}
                            accept="image/jpeg, image/png, image/gif"
                            variant="button"
                        >
                            이미지 선택
                        </FileInput>
                    </FormField>

                    <Table
                        columnDefinitions={[
                            {
                                id: "name",
                                header: "파일 이름",
                                cell: file => file.name
                            },
                            {
                                id: "size",
                                header: "파일 크기",
                                cell: file => file.size / 1000 + "KB"
                            }
                        ]}
                        items={inputImageFile}
                        empty="No files"
                    />

                    <FormField label="제목" description="글 제목을 입력해주세요">
                        <Input
                            value={inputTitle}
                            onChange={({ detail }) => setInputTitle(detail.value)}
                        />
                    </FormField>

                    <FormField label="내용" description="글 내용을 입력해주세요 (물건 상태, 거래 가능한 시간 등)" stretch>
                        <Textarea
                            value={inputContent}
                            onChange={({ detail }) => setInputContent(detail.value)}
                            rows={15}
                        />
                    </FormField>

                    <Button
                        variant="primary"
                        type="submit"
                        loading={isPostLoading}
                    >
                        등록
                    </Button>
                </SpaceBetween>
            </Container>
        </form>
    );
}
