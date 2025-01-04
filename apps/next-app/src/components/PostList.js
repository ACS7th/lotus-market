"use client";

import * as React from "react";
import axios from "axios";
import Cards from "@cloudscape-design/components/cards";
import Box from "@cloudscape-design/components/box";
import SpaceBetween from "@cloudscape-design/components/space-between";
import Button from "@cloudscape-design/components/button";
import TextFilter from "@cloudscape-design/components/text-filter";
import Header from "@cloudscape-design/components/header";
import Pagination from "@cloudscape-design/components/pagination";
import { format, parseISO, set } from "date-fns";
import { Container } from "@cloudscape-design/components";
import Image from "next/image";
import { useEffect } from "react";

export default function PostList() {
    const [posts, setPosts] = React.useState([]);
    const [currentPage, setCurrentPage] = React.useState(1);
    const [isPostLoading, setIsPostLoading] = React.useState(true);
    const [filterText, setFilterText] = React.useState("");
    const itemsPerPage = 6;

    useEffect(() => {
        const fetchPosts = async () => {

            try {
                const response = await axios.get(`/api/post`);
                setPosts(response.data);
                setIsPostLoading(false);
            } catch (error) {
                console.error("Error fetching posts:", error);
            }
        };

        fetchPosts();
    }, []);

    const setFilteredPosts = async () => {
        const response = await axios.get(`/api/post/search?item=${filterText}`);
        setPosts(response.data);
    }

    const paginatedPosts = React.useMemo(() => {
        const startIndex = (currentPage - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
        return posts.slice(startIndex, endIndex);
    }, [posts, currentPage]);

    return (
        <Container>
            <Cards
                ariaLabels={{
                    itemSelectionLabel: (e, t) => `Select ${t.title}`,
                    selectionGroupLabel: "Item selection",
                }}
                cardDefinition={{
                    header: (item) => (
                        <Header>{item.title}</Header>
                    ),
                    sections: [
                        {
                            id: "item",
                            content: (item) => item.item,
                        },
                        {
                            id: "image",
                            content: (item) => (item.imageUrl ?
                                <Image
                                    layout="responsive"
                                    width={100}
                                    height={100}
                                    src={item.imageUrl}
                                    alt="no image"
                                >
                                </Image>
                                :
                                "이미지를 등록하지 않은 글입니다"
                            )
                        },
                        {
                            id: "body",
                            header: "내용",
                            content: (item) => (
                                <div style={{ whiteSpace: "pre-line" }}>
                                    {item.content}
                                </div>
                            ),
                        },
                        {
                            id: "purchaseDate",
                            header: "구매일",
                            content: (item) => format(parseISO(item.purchaseDate), 'yyyy-MM-dd'),
                        },
                        {
                            id: "timestamp",
                            header: "등록 시간",
                            content: (item) => format(parseISO(item.timestamp), 'yyyy-MM-dd HH:mm:ss'),
                        },
                    ],
                }}
                items={paginatedPosts}
                cardsPerRow={[
                    { cards: 1 },
                    { minWidth: 500, cards: 2 },
                ]}
                stickyHeader
                variant="full-page"
                loading={isPostLoading}
                loadingText="게시글 읽는중..."
                empty={
                    <Box
                        margin={{ vertical: "xs" }}
                        textAlign="center"
                        color="inherit"
                    >
                        <SpaceBetween size="m">
                            <b>No posts available</b>
                        </SpaceBetween>
                    </Box>
                }
                filter={
                    <TextFilter
                        filteringPlaceholder="Search Items..."
                        filteringText={filterText}
                        filteringClearAriaLabel="asd"
                        onChange={({ detail }) => setFilterText(detail.filteringText)}
                        onDelayedChange={() => setFilteredPosts()}
                    />
                }
                header={
                    <Header variant="h1" description="현재 판매중인 중고 물품입니다">
                        연근 게시판
                    </Header>
                }
                pagination={
                    <Pagination
                        currentPageIndex={currentPage}
                        pagesCount={Math.ceil(posts.length / itemsPerPage)}
                        onChange={({ detail }) => setCurrentPage(detail.currentPageIndex)}
                    />
                }
            />
        </Container>
    );
}
